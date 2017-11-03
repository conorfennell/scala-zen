lazy val para = taskKey[Unit]("paradox")

lazy val root = (project in file(".")).
  enablePlugins(ParadoxPlugin).
  settings(
    name := "Scala Zen",
    paradoxTheme := Some(builtinParadoxTheme("generic")),
    para := {
      IO.delete(sourceDirectory.value)
      IO.delete(baseDirectory.value / "target")
      IO.copyFile(baseDirectory.value / "README.md", baseDirectory.value / "index.md")
      IO.copyDirectory(baseDirectory.value, sourceDirectory.value / "main" / "paradox")
    },
  )