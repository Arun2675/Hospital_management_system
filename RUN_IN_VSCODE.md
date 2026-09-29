# Running the Hospital Management System in VS Code

## Recommended
1. Extract the ZIP completely.
2. Open the **Hospital Management System** folder in VS Code (the folder containing `src` and `.vscode`).
3. Make sure the **Extension Pack for Java** is installed.
4. Wait for VS Code/JDT to finish loading the Java project.
5. Open `src/com/hospital/main/HospitalApp.java` and click **Run**, or use the Run and Debug panel and select **Run Hospital Management System**.

The project is configured with `.vscode/settings.json` so that `src` is explicitly treated as the Java source folder.

## Terminal alternative
From the project root in PowerShell:

```powershell
javac -d bin src\com\hospital\model\*.java src\com\hospital\service\*.java src\com\hospital\main\*.java
java -cp bin com.hospital.main.HospitalApp
```

Or double-click `run.bat` on Windows.

## If VS Code still shows old red errors
Use:
**Ctrl+Shift+P → Java: Clean Java Language Server Workspace**
Then restart/reload VS Code and open the project root again.
