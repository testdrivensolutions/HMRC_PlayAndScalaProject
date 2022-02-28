package models

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Json, Reads, Writes}

case class QuizResults (
                     category: String,
                     qType: String,
                     difficulty: String,
                     question: String,
                     correct_answer: String,
                     incorrect_answers: Seq[String]
                   )

object QuizResults {

  implicit val quizResultWrites = new Writes[QuizResults] {
    def writes(quizResult: QuizResults) = Json.obj(
      "category" -> quizResult.category,
      "type" -> quizResult.qType,
      "difficulty" -> quizResult.difficulty,
      "question" -> quizResult.question,
      "correct_answer" -> quizResult.correct_answer,
      "incorrect_answers" -> quizResult.incorrect_answers,
    )
  }

  implicit val quizResultReads: Reads[QuizResults] = (
    (JsPath \ "category").read[String] and
      (JsPath \ "type").read[String] and
      (JsPath \ "difficulty").read[String] and
      (JsPath \ "question").read[String] and
      (JsPath \ "correct_answer").read[String] and
      (JsPath \ "incorrect_answers").read[Seq[String]]
    )(QuizResults.apply _)

}

case class QuizResult(response_code: Int, quizResults: Seq[QuizResults])


object QuizResult {

  implicit val quizResultWrites = new Writes[QuizResult] {
    def writes(quizResult: QuizResult) = Json.obj(
      "response_code" -> quizResult.response_code,
      "results" -> quizResult.quizResults.toString()
    )
  }

  implicit val quizResultReads: Reads[QuizResult] = (
    (JsPath \ "response_code").read[Int] and
      (JsPath \ "results").read[Seq[QuizResults]]
    )(QuizResult.apply _)

}