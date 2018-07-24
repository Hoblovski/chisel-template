import chisel3._
import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class RegBundleModuleTest0(c: RegBundleModule) extends PeekPokeTester(c) {
    poke(c.io.dataIn.v1, "h_dead_beef".U(32.W))
    poke(c.io.dataIn.v2, "h_c0ff_ee00".U(32.W))
    step(1)
    poke(c.io.dataIn.v1, "h_1".U(32.W))
    poke(c.io.dataIn.v2, "h_2".U(32.W))
    expect(c.io.data.v1, "h_dead_beef".U(32.W))
    expect(c.io.data.v2, "h_c0ff_ee00".U(32.W))
    step(1)
    expect(c.io.data.v1, "h_1".U(32.W))
    expect(c.io.data.v2, "h_2".U(32.W))
}

class RegBundleTests extends ChiselFlatSpec {
  "Stupid test" should "pass test 0" in {
    Driver(() => new RegBundleModule) {
      c => new RegBundleModuleTest0(c)
    } should be (true)
  }
}
