// See LICENSE.txt for license details.
package core

import chisel3.iotesters.{Driver, TesterOptionsManager}
import merl.uit.tilelink.{MasterInterface, SlaveInterface}
import utils.TutorialRunner
import chisel3._
import peripherals.{GPIOController, UartController}
import soc.{DCCMController, DataMem, ICCMController, InstructionMem, Soc}
import uart_testbench.Top

object Launcher {
  val examples = Map(
      "AluControl" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new AluControl(), manager) {
          (c) => new AluControlTests(c)
        }
      },
      "Alu" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Alu(), manager) {
          (c) => new AluTests(c)
        }
      },
      "InstructionTypeDecode" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new InstructionTypeDecode(), manager) {
          (c) => new InstructionTypeDecodeTests(c)
        }
      },
      "ControlDecode" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new ControlDecode(), manager) {
          (c) => new ControlDecodeTests(c)
        }
      },
      "Control" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Control(), manager) {
          (c) => new ControlTests(c)
        }
      },
      "ImmediateGeneration" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new ImmediateGeneration(), manager) {
          (c) => new ImmediateGenerationTests(c)
        }
      },
      "RegisterFile" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new RegisterFile(), manager) {
          (c) => new RegisterFileTests(c)
        }
      },
      "Core" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Core(), manager) {
          (c) => new CoreTests(c)
        }
      },
      "Pc" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Pc(), manager) {
          (c) => new PcTests(c)
        }
      },
      "InstructionMem" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new InstructionMem(), manager) {
          (c) => new InstructionMemTests(c)
        }
      },
      "Jalr" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Jalr(), manager) {
          (c) => new JalrTests(c)
        }
      },
      "DataMem" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new DataMem(), manager) {
          (c) => new DataMemTests(c)
        }
      },
      "IF_ID" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new IF_ID(), manager) {
          (c) => new IF_IDTests(c)
        }
      },
      "ID_EX" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new ID_EX(), manager) {
          (c) => new ID_EXTests(c)
        }
      },
      "EX_MEM" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new EX_MEM(), manager) {
          (c) => new EX_MEMTests(c)
        }
      },
      "MEM_WB" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new MEM_WB(), manager) {
          (c) => new MEM_WBTests(c)
        }
      },
      "ForwardUnit" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new ForwardUnit(), manager) {
          (c) => new ForwardUnitTests(c)
        }
      },
      "ForwardUnitMem" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new ForwardUnitMem(), manager) {
          (c) => new ForwardUnitMemTests(c)
        }
      },
      "HazardDetection" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new HazardDetection(), manager) {
          (c) => new HazardDetectionTests(c)
        }
      },
      "BranchLogic" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new BranchLogic(), manager) {
          (c) => new BranchLogicTests(c)
        }
      },
      "DecodeForwardUnit" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new DecodeForwardUnit(), manager) {
          (c) => new DecodeForwardUnitTests(c)
        }
      },
      "StructuralDetector" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new StructuralDetector(), manager) {
          (c) => new StructuralDetectorTests(c)
        }
      },
      "Fetch" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Fetch(), manager) {
          (c) => new FetchTests(c)
        }
      },
      "Decode" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Decode(), manager) {
          (c) => new DecodeTests(c)
        }
      },
      "WriteBack" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new WriteBack(), manager) {
          (c) => new WriteBackTests(c)
        }
      },
      "Execute" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Execute(), manager) {
          (c) => new ExecuteTests(c)
        }
      },
      "MemoryStage" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new MemoryStage(), manager) {
          (c) => new MemoryStageTests(c)
        }
      },
      "Staller" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Staller(), manager) {
          (c) => new StallerTests(c)
        }
      },
      "MasterInterface" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new MasterInterface(1.U, false), manager) {
          (c) => new MasterInterfaceTests(c)
        }
      },
      "SlaveInterface" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new SlaveInterface(forFetch = true), manager) {
          (c) => new SlaveInterfaceTests(c)
        }
      },
      "ICCMController" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new ICCMController(), manager) {
          (c) => new ICCMControllerTests(c)
        }
      },
      "FetchBusController" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new FetchBusController(), manager) {
          (c) => new FetchBusControllerTests(c)
        }
      },
      "Soc" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Soc(), manager) {
          (c) => new SocTests(c, "/home/sajjad/Desktop/Buraq-mini/RV32i/instructions.txt")
        }
      },
      "DCCMController" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new DCCMController(), manager) {
          (c) => new DCCMControllerTests(c)
        }
      },
      "GPIOController" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new GPIOController(), manager) {
          (c) => new GPIOControllerTests(c)
        }
      },
      "UartController" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new UartController(50000000, 115200), manager) {
          (c) => new UartControllerTests(c)
        }
      },
      "Top" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Top(), manager) {
          (c) => new TopTests(c)
        }
      }
  )
  def main(args: Array[String]): Unit = {
    TutorialRunner("examples", examples, args)
  }
}