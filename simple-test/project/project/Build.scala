import sbt._

object PluginDef extends Build {
  override lazy val projects = Seq(root)
  lazy val root = Project("plugins", file(".")) dependsOn(scctPlugin)
  lazy val scctPlugin = uri("file:///Users/mtkopone/projects/scct-root/sbt-scct")
}