; Variables:
#define MyAppName "Password Saver"
#define MyAppVersion "6.0.1"
#define MyAppPublisher "Tandem"
#define MyAppURL "https://github.com/maucon/PasswordSaverV6"   
#define MyAppExeName "PasswordSaverV6.exe"
;#define MyAppIconName "MyAppName.ico"

[Setup]
; NOTE: The value of AppId uniquely identifies this application. Do not use the same AppId value in installers for other applications.
AppId={{7C28D8E1-42E6-4AC6-A52D-4121FBC059C2}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL} 
AppVerName={#MyAppName}
DefaultDirName={autopf}\{#MyAppName}   
DefaultGroupName={#MyAppName} 
Compression=lzma2   
SolidCompression=yes   
WizardStyle=modern
; Filename of the Setup exe
OutputBaseFilename=PasswordSaverV6
; Changes the Folder where the Setup exe is created
OutputDir=Installer
; will use x64 folder on 64bit PC
ArchitecturesInstallIn64BitMode=x64  
; will show a checkbox for making star menu folder optional
AllowNoIcons=yes  
; Skips the Language Selection if the current PCs language is listed in [Languages]
ShowLanguageDialog=auto
; Makes The Setup Close any Program that uses the Files that the Setup wants zu change (other option: force)
CloseApplications=yes
; Filters which Files are checked by "CloseApplications"  (default: *.exe,*.dll,*.chm)
CloseApplicationsFilter=*.*

[Dirs]
Name: "{userdocs}\PasswordSaverV6\database"

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "german"; MessagesFile: "compiler:Languages\German.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked   

[Files]
Source: "build\jpackage\PasswordSaver-6.0.0\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs

; Creates Shortcuts
; Parameters, HotKeys, WorkingDirectories etc can be specified, see https://jrsoftware.org/ishelp/index.php?topic=iconssection
; common Shortcut Folderconstants:  autoprograms (Prorgams Folder on Start Menu) | autoappdata | uninstallexe | autodocs (Documents Folder) | usersavedgames | autostartup | group | autodesktop
[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Flags: createonlyiffileexists;
Name: "{autodesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon; Flags: createonlyiffileexists;
Name: "{app}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Flags: createonlyiffileexists;

; Specifies Programs that are run after installation but before the final page of the Setup  
; use Flag "shellexec" when file is not directly runnable (for example .txt or a folder)
[Run] 
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent    
   
[UninstallDelete]
Type: filesandordirs; Name: "{userdocs}\PasswordSaverV6\database"
