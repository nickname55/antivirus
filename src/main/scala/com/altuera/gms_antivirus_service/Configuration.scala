// © LLC "Altuera", 2019
package com.altuera.gms_antivirus_service

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

object Configuration {

  private val config = ConfigFactory.load()

  def uploadBaseDir: String = getStringProperty("uploadBaseDir")

  def storageBaseDir: String = getStringProperty("storageBaseDir")

  def genesysApiBaseUrl: String = getStringProperty("genesysApi.baseUrl")

  //Messages
  def messagePleaseWait: Option[String] = getStringOpt("customNotices.pleaseWait")

  def messageIsSafeFileCopy: Option[String] = getStringOpt("customNotices.isSafeFileCopy")

  def messageCheckingStartedAndLinkToOriginal: Option[String] = getStringOpt("customNotices.checkingStartedAndLinkToOriginal")

  def messageIsSafeFileAndLinkToOriginal: Option[String] = getStringOpt("customNotices.isSafeFileAndLinkToOriginal")

  def messageIsInfectedFile: Option[String] = getStringOpt("customNotices.isInfectedFile")

  def messageIsCorruptedFile: Option[String] = getStringOpt("customNotices.isCorruptedFile")

  def messageFileNotFound: Option[String] = getStringOpt("customNotices.fileNotFound")

  //File types
  private val avFileExtensionsLists = config.getConfig("avApi.fileExtensionsLists")

  def avExtensionsForThreadExtraction: List[String] = avFileExtensionsLists.getStringList("forThreadExtraction").asScala.toList

  def avExtensionsForThreadEmulation: List[String] = avFileExtensionsLists.getStringList("forThreadEmulation").asScala.toList

  def avExtensionsForConvertToPdf: List[String] = avFileExtensionsLists.getStringList("forConvertToPdf").asScala.toList


  //Threat Prevention API (Antivirus = av)
  def avApiServerAddress: String = getStringProperty("avApi.serverAddress")

  def avApiVersion: String = getStringProperty("avApi.apiVersion")

  def avApiKey: String = getStringProperty("avApi.apiKey")

  //Antivirus http-client, retry parameters
  def avRetryMaximumWaitTimeSeconds: Int = getIntProperty("avApi.retry.maximumWaitTimeSeconds")

  def avRetryPauseBetweenAttemptsMilliseconds: Int = getIntProperty("avApi.retry.pauseBetweenAttemptsMilliseconds")

  def avRetryMaxNumberOfTimes: Int = getIntProperty("avApi.retry.maxNumberOfTimes")

  private def getStringOpt(path: String): Option[String] = {
    Some(path).filter(config.hasPath).map(config.getString)
  }

  private def getStringProperty(path: String): String = {
    Some(path)
      .filter(config.hasPath).map(config.getString)
      .getOrElse(throw new IllegalStateException(s"Required config property [$path] missing"))
  }

  private def getIntProperty(path: String): Int = {
    Some(path)
      .filter(config.hasPath).map(config.getInt)
      .getOrElse(throw new IllegalStateException(s"Required config property [$path] missing"))
  }
}
