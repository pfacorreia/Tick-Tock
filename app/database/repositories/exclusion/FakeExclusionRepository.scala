package database.repositories.exclusion

import api.dtos.{ ExclusionDTO, TaskDTO }
import api.services.{ Criteria, DayType, SchedulingType }
import api.utils.DateUtils._
import database.mappings.ExclusionMappings.ExclusionRow
import database.mappings.TaskMappings.TaskRow

import scala.concurrent.Future

class FakeExclusionRepository extends ExclusionRepository {

  def exclusionRowToExclusionDTO(exclusion: ExclusionRow): ExclusionDTO = {
    ExclusionDTO("dsa1", "asd1", Some(stringToDateFormat("2030-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss")))
  }

  def exclusionDTOToExclusionRow(exclusion: ExclusionDTO): ExclusionRow = {
    ExclusionRow("asd1", "dsa1", Some(stringToDateFormat("2030-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss")))
  }

  /**
   * Selects all rows from the exclusions table on the database.
   *
   * @return all exclusions in the database
   */
  def selectAllExclusions: Future[Seq[ExclusionDTO]] = {
    Future.successful(Seq(
      ExclusionDTO("dsa1", "asd1", Some(stringToDateFormat("2030-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss"))),
      ExclusionDTO("dsa2", "asd2", None, Some(15), None, Some(DayType.Weekday), None, Some(2030)),
      ExclusionDTO("dsa3", "asd3", None, None, Some(3), None, Some(5), None, Some(Criteria.Third))))
  }

  /**
   * Selects an exclusion from the database given an id
   *
   * @return An exclusionDTO containing the selected exclusion
   */
  def selectExclusion(id: String): Future[Option[ExclusionDTO]] = {
    Future.successful(Some(ExclusionDTO("dsa1", "asd1", Some(stringToDateFormat("2030-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss")))))
  }

  def selectExclusionsByTaskId(id: String): Future[Option[Seq[ExclusionDTO]]] = {
    Future.successful(Some(Seq(ExclusionDTO("dsa1", "asd1", Some(stringToDateFormat("2030-01-01 12:00:00", "yyyy-MM-dd HH:mm:ss"))))))
  }

  /**
   * Deletes all exclusions from the exclusions table on the database.
   */
  def deleteAllExclusions: Future[Int] = {
    Future.successful(3)
  }

  /**
   * Deletes an exclusion from the database given an id
   * @param id - identifier of the exclusion to be deleted.
   * @return An Int representing the number of rows deleted.
   */
  def deleteExclusionById(id: String): Future[Int] = {
    Future.successful(1)
  }

  /**
   * Method that inserts an exclusion (row) on the exclusions table on the database.
   *
   * @param file ExclusionDTO to be inserted on the database.
   */
  def insertInExclusionsTable(file: ExclusionDTO): Future[Boolean] = {
    Future.successful(true)
  }

}