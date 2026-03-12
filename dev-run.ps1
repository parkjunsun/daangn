# 개발 서버 시작 스크립트 (자동 클린 포함)

Write-Host "===================================" -ForegroundColor Cyan
Write-Host "Daangn Clone - Development Server" -ForegroundColor Cyan
Write-Host "===================================" -ForegroundColor Cyan

Write-Host "`n서버를 시작합니다..." -ForegroundColor Yellow
Write-Host "자동으로 clean이 실행됩니다 (build.gradle 설정)" -ForegroundColor Gray

./gradlew bootRun

