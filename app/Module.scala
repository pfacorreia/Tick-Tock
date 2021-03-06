import api.services.StartUpService
import api.utils.{ DefaultUUIDGenerator, UUIDGenerator }
import com.google.inject.AbstractModule
import database.repositories.exclusion.{ ExclusionRepository, ExclusionRepositoryImpl }
import database.repositories.file.{ FileRepository, FileRepositoryImpl }
import database.repositories.scheduling.{ SchedulingRepository, SchedulingRepositoryImpl }
import database.repositories.task.{ TaskRepository, TaskRepositoryImpl }
import database.utils.DatabaseUtils._
import executionengine.{ ExecutionManager, ExecutionManagerImpl }
import slick.jdbc.MySQLProfile.api._

class Module extends AbstractModule {

  def configure(): Unit = {
    bind(classOf[StartUpService]).asEagerSingleton()

    val exclusionRepo = new ExclusionRepositoryImpl(DEFAULT_DB)
    val schedulingRepo = new SchedulingRepositoryImpl(DEFAULT_DB)

    bind(classOf[Database]).toInstance(DEFAULT_DB)
    bind(classOf[FileRepository]).toInstance(new FileRepositoryImpl(DEFAULT_DB))
    bind(classOf[TaskRepository]).toInstance(new TaskRepositoryImpl(DEFAULT_DB, exclusionRepo, schedulingRepo))
    bind(classOf[ExclusionRepository]).toInstance(new ExclusionRepositoryImpl(DEFAULT_DB))
    bind(classOf[SchedulingRepository]).toInstance(new SchedulingRepositoryImpl(DEFAULT_DB))
    bind(classOf[UUIDGenerator]).toInstance(new DefaultUUIDGenerator)
    bind(classOf[ExecutionManager]).toInstance(new ExecutionManagerImpl)
  }
}
