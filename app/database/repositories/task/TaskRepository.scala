package database.repositories.task

import api.dtos.TaskDTO
import database.mappings.TaskMappings.TaskRow

import scala.concurrent.Future

trait TaskRepository {

  def taskRowToTaskDTO(maybeTask: Option[TaskRow]): Future[Option[TaskDTO]]

  def taskDTOToTaskRow(task: TaskDTO): Future[TaskRow]

  /**
   * Selects all rows from the tasks table on the database.
   * @return all tasks in the database
   */
  def selectAllTasks(offset: Option[Int] = None, limit: Option[Int] = None): Future[Seq[TaskDTO]]

  /**
   * Select a single task from the database by giving its id.
   *
   * @param id - the identifier of the task.
   * @return a TaskDTO of the selected task.
   */
  def selectTask(id: String): Future[Option[TaskDTO]]

  /**
   *
   * Select the fileId from a task by giving its taskId.
   *
   * @param id - the identifier of the task.
   * @return a String containing the fileId.
   */
  def selectFileIdByTaskId(id: String): Future[Option[String]]

  /**
   * Select the totalOccurrences from a task on the database by giving its id.
   *
   * @param id - the identifier of the task.
   * @return an Int representing the totalOccurrences of the task.
   */
  def selectTotalOccurrencesByTaskId(id: String): Future[Option[Int]]

  /**
   * Select the currentOccurrences from a task on the database by giving its id.
   *
   * @param id - the identifier of the task we want to select.
   * @return an Int representing the currentOccurrences of the task.
   */
  def selectCurrentOccurrencesByTaskId(id: String): Future[Option[Int]]

  /**
   *
   * Reduces the currentOccurrences from a task on the database by 1 by giving its id.
   *
   * @param id - the identifier of the task we want to select.
   */
  def decrementCurrentOccurrencesByTaskId(id: String): Future[Int]

  /**
   * Deletes all tasks from the tasks table on the database.
   */
  def deleteAllTasks: Future[Int]

  /**
   * Given a an id deletes the corresponding task
   *
   * @param id - identifier of the task to be deleted
   */
  def deleteTaskById(id: String): Future[Int]

  /**
   * Updates a single task given its identifier.
   *
   * @param id   - identifier of the task to be updated
   * @param task - information to update the task with
   * @return an Int with information of the updated task
   */
  def updateTaskById(id: String, task: TaskDTO): Future[Boolean]

  /**
   * Inserts a task (row) on the tasks table on the database.
   *
   * @param task TaskDTO to be inserted.
   */
  def insertInTasksTable(task: TaskDTO): Future[Boolean]
}
