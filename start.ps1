$ErrorActionPreference = 'Stop'
Set-Location -LiteralPath $PSScriptRoot
$pythonBin = Join-Path $env:LOCALAPPDATA 'Python\pythoncore-3.14-64\python.exe'
if (-not (Test-Path -LiteralPath $pythonBin)) { $pythonBin = (Get-Command python -ErrorAction Stop).Source }
& $pythonBin (Join-Path $PSScriptRoot 'scripts\manage.py') start
if ($LASTEXITCODE -ne 0) { throw 'Khong the khoi dong. Xem log trong .runtime.' }
