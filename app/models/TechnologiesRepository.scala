package models

import javax.inject.Inject

import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.Cursor.FailOnError
import reactivemongo.api.ReadPreference
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{BSONDocument, BSONDocumentWriter, BSONObjectID}
import reactivemongo.play.json.collection.JSONCollection
import utilities.DateTimeUtility
import models.technologiesJsonFormats._
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

// this is not an unused import contrary to what intellij suggests, do not optimize
import reactivemongo.play.json.BSONFormats.BSONObjectIDFormat
import reactivemongo.play.json.BSONFormats.BSONDateTimeFormat


case class CategoryInfo(categoryName: String,
                        _id: BSONObjectID = BSONObjectID.generate
                       )

object technologiesJsonFormats {

  import play.api.libs.json.Json

  implicit val technologiesFormat = Json.format[CategoryInfo]

}
class TechnologiesRepository  @Inject()(reactiveMongoApi: ReactiveMongoApi) {

  import play.modules.reactivemongo.json._

  protected def collection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection[JSONCollection]("technologies"))

  def upsert(category: CategoryInfo)(implicit ex: ExecutionContext): Future[WriteResult] = {
    val selector = BSONDocument("categoryName" -> category.categoryName)

    collection.flatMap(_.update(selector, selector, upsert = true))
  }

  def getCategories(implicit ex: ExecutionContext): Future[List[CategoryInfo]] = {
    collection.
      flatMap(jsonCollection =>
        jsonCollection.
          find(Json.obj()).
          cursor[CategoryInfo](ReadPreference.Primary)
          .collect[List](-1, FailOnError[List[CategoryInfo]]()))
  }
}
