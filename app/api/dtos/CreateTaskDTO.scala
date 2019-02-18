package api.dtos

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.{Date, TimeZone, UUID}

import database.utils.DatabaseUtils._
import api.services.PeriodType.PeriodType
import api.services.SchedulingType.SchedulingType
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import database.repositories.FileRepositoryImpl

import scala.concurrent.{ExecutionContext, Future}

case class CreateTaskDTO(
                     fileName: String,
                     taskType: SchedulingType,
                     startDateAndTime: Option[String] = None,
                     periodType: Option[PeriodType] = None,
                     period: Option[Int] = None,
                     endDateAndTime: Option[String] = None,
                     occurrences: Option[Int] = None,
                     schedulings: Option[List[CreateSchedulingDTO]] = None,
                     timezone: Option[String] = None
                   )

object CreateTaskDTO {

  implicit val ec = ExecutionContext.global

  /**
    * Implicit that defines how a CreateTaskDTO is read from the JSON request.
    * This implicit is used on the TaskController when Play's "validate" method is called.
    */
  implicit val createTaskReads: Reads[CreateTaskDTO] = (
      (JsPath \ "fileName").read[String] and
      (JsPath \ "taskType").read[String] and
      (JsPath \ "startDateAndTime").readNullable[String] and
      (JsPath \ "periodType").readNullable[String] and
      (JsPath \ "period").readNullable[Int] and
      (JsPath \ "endDateAndTime").readNullable[String] and
      (JsPath \ "occurrences").readNullable[Int] and
      (JsPath \ "schedulings").readNullable[List[CreateSchedulingDTO]] and
      (JsPath \ "timezone").readNullable[String]
    ) (CreateTaskDTO.apply _)

  /**
    * Implicit that defines how a CreateTaskDTO is written to a JSON format.
    */
  implicit val createTaskWrites: OWrites[CreateTaskDTO] = Json.writes[CreateTaskDTO]
}