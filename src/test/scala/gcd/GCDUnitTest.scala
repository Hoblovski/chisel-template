// See README.md for license details.

package gcd

import chisel3._
import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class GCDTesterMod extends Module {
  val io = IO(new Bundle {
    val i = Input(UInt(32.W))
    val o = Output(UInt(32.W))
  })

  val m1 = Module(new GCD())
  m1.io.i := io.i

  val m2 = Module(new GCD())
  io.o := m2.io.o

//  val t = Reg(UInt(32.W))
//  t := m1.io.o
//  m2.io.i := t

  when (true.B) { 
    m1.io.o <> m2.io.i
  } .otherwise {
    m1.io.o <> m2.io.i
  }
}

class GCDUnitTester(c: GCDTesterMod) extends PeekPokeTester(c) {
  poke(c.io.i, 1000.U)
  expect(c.io.o, 1000.U)
  step(1)
  poke(c.io.i, 4000.U)
  expect(c.io.o, 4000.U)
}

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly example.test.GCDTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly example.test.GCDTester'
  * }}}
  */
class GCDTester extends ChiselFlatSpec {
  // Disable this until we fix isCommandAvailable to swallow stderr along with stdout
  private val backendNames = if(false && firrtl.FileUtils.isCommandAvailable(Seq("verilator", "--version"))) {
    Array("firrtl", "verilator")
  }
  else {
    Array("firrtl")
  }
  for ( backendName <- backendNames ) {
    "GCD" should s"calculate proper greatest common denominator (with $backendName)" in {
      Driver(() => new GCDTesterMod, backendName) {
        c => new GCDUnitTester(c)
      } should be (true)
    }
  }

  "Basic test using Driver.execute" should "be used as an alternative way to run specification" in {
    iotesters.Driver.execute(Array(), () => new GCDTesterMod) {
      c => new GCDUnitTester(c)
    } should be (true)
  }

  "using --backend-name verilator" should "be an alternative way to run using verilator" in {
    if(backendNames.contains("verilator")) {
      iotesters.Driver.execute(Array("--backend-name", "verilator"), () => new GCDTesterMod) {
        c => new GCDUnitTester(c)
      } should be(true)
    }
  }

  "running with --is-verbose" should "show more about what's going on in your tester" in {
    iotesters.Driver.execute(Array("--is-verbose"), () => new GCDTesterMod) {
      c => new GCDUnitTester(c)
    } should be(true)
  }

  "running with --fint-write-vcd" should "create a vcd file from your test" in {
    iotesters.Driver.execute(Array("--fint-write-vcd"), () => new GCDTesterMod) {
      c => new GCDUnitTester(c)
    } should be(true)
  }
}
