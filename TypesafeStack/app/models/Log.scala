package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import java.util.Date

case class Log(id: Long, name: String, line: String, createdAt: Date, updatedAt: Date)

object Log {

  val simple = 
    get[Long]("id") ~ 
    get[String]("name") ~ 
    get[String]("line") ~
    get[Date]("created_at") ~
    get[Date]("updated_at") map { 
      case id ~ name ~ line ~ createdAt ~ updatedAt => 
        Log(id, name, line, createdAt, updatedAt)
    }

  def findRecent(limit: Int = 10): Seq[Log] = DB withConnection { implicit conn =>
    SQL("select * from logs order by created_at desc limit {limit}")
      .on('limit -> limit).as(Log.simple *)
  }

  def create(name: String, line:String): Log = DB withTransaction { implicit conn =>
    val now = new Date
    SQL("""
      insert into logs (name, line, created_at, updated_at) 
      values ({name}, {line}, {createdAt}, {updatedAt})
    """)
     .on('name -> name, 'line -> line, 'createdAt -> now, 'updatedAt -> now)
     .executeUpdate()
    val id = SQL("select last_insert_id() id").as(get[Long]("id").single)
    Log(id, name, line, now, now)
  }

}

