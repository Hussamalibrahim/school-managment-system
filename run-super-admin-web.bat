@echo off
title EduCloud - Super Admin Platform Portal
echo ===================================================
echo   EduCloud Super Admin Portal (Multi-Tenancy)
echo ===================================================
echo Starting Web Server on http://localhost:5173 ...
start "" "http://localhost:5173"
python -m http.server 5173 --directory super-admin-web
pause
