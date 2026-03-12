# Daangn Clone - 개발 유틸리티 스크립트

# 완전 클린 빌드 (캐시 문제 해결용)
Write-Host "===================================" -ForegroundColor Cyan
Write-Host "Daangn Clone - Full Clean Build" -ForegroundColor Cyan
Write-Host "===================================" -ForegroundColor Cyan

Write-Host "`n[1/4] Gradle 데몬 중지..." -ForegroundColor Yellow
./gradlew --stop

Write-Host "`n[2/4] 빌드 디렉토리 삭제..." -ForegroundColor Yellow
if (Test-Path "build") {
    Remove-Item -Recurse -Force "build"
    Write-Host "  ✓ build 폴더 삭제 완료" -ForegroundColor Green
}

if (Test-Path ".gradle") {
    Remove-Item -Recurse -Force ".gradle"
    Write-Host "  ✓ .gradle 폴더 삭제 완료" -ForegroundColor Green
}

Write-Host "`n[3/4] IntelliJ 캐시 정리..." -ForegroundColor Yellow
if (Test-Path ".idea/caches") {
    Remove-Item -Recurse -Force ".idea/caches" -ErrorAction SilentlyContinue
    Write-Host "  ✓ .idea/caches 삭제 완료" -ForegroundColor Green
}

Write-Host "`n[4/4] 클린 빌드 실행..." -ForegroundColor Yellow
./gradlew clean build --refresh-dependencies

Write-Host "`n===================================" -ForegroundColor Cyan
Write-Host "클린 빌드 완료!" -ForegroundColor Green
Write-Host "===================================" -ForegroundColor Cyan
Write-Host "`nIntelliJ에서 File -> Invalidate Caches / Restart 실행을 권장합니다." -ForegroundColor Yellow

