import api.utils.{ FakeUUIDGenerator, UUIDGenerator }
import com.google.inject.AbstractModule
import database.repositories.FileRepository
import database.repositories.exclusion.{ ExclusionRepository, ExclusionRepositoryImpl }
import database.repositories.file.FileRepositoryImpl
import database.repositories.scheduling.{ SchedulingRepository, SchedulingRepositoryImpl }
import database.repositories.task.{ TaskRepository, TaskRepositoryImpl }
import database.utils.DatabaseUtils._
import executionengine.{ ExecutionManager, FakeExecutionManager }
import slick.jdbc.MySQLProfile.api._

class Module extends AbstractModule {

  def configure(): Unit = {
    bind(classOf[Database]).toInstance(TEST_DB)
    bind(classOf[FileRepository]).toInstance(new FileRepositoryImpl(TEST_DB))
    bind(classOf[TaskRepository]).toInstance(new TaskRepositoryImpl(TEST_DB))
    bind(classOf[ExclusionRepository]).toInstance(new ExclusionRepositoryImpl(TEST_DB))
    bind(classOf[SchedulingRepository]).toInstance(new SchedulingRepositoryImpl(TEST_DB))
    bind(classOf[UUIDGenerator]).toInstance(new FakeUUIDGenerator)
    bind(classOf[ExecutionManager]).toInstance(new FakeExecutionManager)
  }
}