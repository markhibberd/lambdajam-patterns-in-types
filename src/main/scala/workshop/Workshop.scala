package workshop

import challenge1.Result
import challenge5.ReaderT
import challenge6.WriterT
import challenge7.StateT
import challenge0.{Monad, Monoid}
import workshop.CommandLineTools.FileSystem

object Workshop {
  import CommandLineTools._
  def main(args: Array[String]) {
    parseArgs(args.toList) match {
      case Left(err) => sys.error(err)
      case Right(commands) =>
    }
  }

  def parseArgs(args: List[String], commands: List[(FileSystem) => Status] = Nil) : Either[String, List[(FileSystem) => Status]] = args match {
    case Nil => Right(commands)
    case command :: src :: dest :: tail if command == "cp" => parseArgs(tail, commands :+ Commands.copy(src, dest))
    case command :: src :: contents :: tail if command == "touch" => parseArgs(tail, commands :+ Commands.create(src, contents))
    case command :: src :: tail if command == "rm" => parseArgs(tail, commands :+ Commands.delete(src))
    case badCommand :: tail => Left(s"bad command ($badCommand)")
  }

}

object CommandLineTools {
  type Status = String

  type V[A] =             OperationResult[A]
  type WV[A] =            WriterT[V, Log, A]
  type SWV[A] =           StateT[WV, OperationStatistics, A]
  type RSMV[A] =          ReaderT[SWV, FileSystem, A]

  trait FileSystem {
    import java.io.File
  }

  case class OperationStatistics(fileCount: Long)
  object OperationStatistics {
    implicit def OperationStatisticsMonoid: Monoid[OperationStatistics] = new Monoid[OperationStatistics] {
      def zero = OperationStatistics(0)
      def append(l: OperationStatistics, r: => OperationStatistics): OperationStatistics =
        OperationStatistics(l.fileCount + r.fileCount)
    }
  }

  case class Log(messages: Vector[String])
  object Log {
      implicit def LogMonoid: Monoid[Log] = new Monoid[Log] {
        def zero = Log(Vector())
        def append(l: Log, r: => Log): Log =
          Log(l.messages ++ r.messages)
      }
    }
  type OperationResult[T] = Result[T]

  case class FileCommand[A](run: RSMV[A]) {
    def map[B](f: A => B): FileCommand[B] = FileCommand(run.map(f))
    def flatMap[B](f: A => FileCommand[B]) : FileCommand[B] = FileCommand(run.flatMap(f andThen (_.run)))
  }

  object FileCommand {
    def execute(f: (FileSystem) => Status) : FileCommand[Status] = FileCommand(for {

    } yield status)
    def value[A](a: => A): FileCommand[A] = FileCommand(Monad[RSMV].point(a))
  }

  object Commands {
    def copy(src: String, dest: String)(fileSystem: FileSystem) : Status = {
      ""
    }
    def delete(src: String)(fileSystem: FileSystem) : Status = {
      ""
    }
    def create(src: String, contents: String)(fileSystem: FileSystem) : Status = {
      ""
    }
  }
}
