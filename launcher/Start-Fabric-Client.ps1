param(
    [string]$Username = "",
    [ValidateSet('1.21.1')]
    [string]$Version = "1.21.1"
)

$ErrorActionPreference = 'Stop'
Set-StrictMode -Version Latest

$repoRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$jarPath = Join-Path $repoRoot 'build\libs\flymenu-1.0.0.jar'
$mcDir = Join-Path $env:APPDATA '.minecraft'
$modsDir = Join-Path $mcDir 'mods'

if (-not (Test-Path $jarPath)) {
    Write-Host "Jar bulunamadi: $jarPath" -ForegroundColor Red
    Write-Host "Once build edin: gradle build" -ForegroundColor Yellow
    exit 1
}

New-Item -ItemType Directory -Path $modsDir -Force | Out-Null
Copy-Item $jarPath (Join-Path $modsDir 'flymenu-1.0.0.jar') -Force

Write-Host "Mod kopyalandi: $modsDir" -ForegroundColor Green

if ([string]::IsNullOrWhiteSpace($Username)) {
    $Username = Read-Host "Minecraft kullanıcı adı"
}

Write-Host "" 
Write-Host "Kullanici: $Username" -ForegroundColor Cyan
Write-Host "Versiyon: $Version" -ForegroundColor Cyan
Write-Host "" 
Write-Host "Internet ve Fabric profile ayarini yapman lazim. Bu script sadece modu yerleştirir ve launcher'i acmaya calisir." -ForegroundColor Yellow
Write-Host "" 

$launcherCandidates = @(
    "$env:LOCALAPPDATA\Programs\Minecraft Launcher\MinecraftLauncher.exe",
    "$env:ProgramFiles\Minecraft Launcher\MinecraftLauncher.exe",
    "$env:ProgramFiles(x86)\Minecraft Launcher\MinecraftLauncher.exe"
)

$launcher = $launcherCandidates | Where-Object { Test-Path $_ } | Select-Object -First 1

if ($launcher) {
    Write-Host "Minecraft Launcher aciliyor..." -ForegroundColor Green
    Start-Process $launcher
} else {
    Write-Host "Minecraft Launcher bulunamadi. Resmi launcher'i acip Fabric profile'i secin." -ForegroundColor Yellow
    Write-Host "Kullanici adi: $Username" -ForegroundColor Cyan
    Write-Host "Versiyon: $Version" -ForegroundColor Cyan
}

Write-Host "" 
Write-Host "Not: Bu legal local Fabric kurulum yoludur. Gercek custom client degil; custom profile ve Fabric kullanilir." -ForegroundColor Magenta
