<?php

return [

    /*
    |--------------------------------------------------------------------------
    | Third Party Services
    |--------------------------------------------------------------------------
    |
    | This file is for storing the credentials for third party services such
    | as Mailgun, Postmark, AWS and more. This file provides the de facto
    | location for this type of information, allowing packages to have
    | a conventional file to locate the various service credentials.
    |
    */

    'postmark' => [
        'key' => env('POSTMARK_API_KEY'),
    ],

    'resend' => [
        'key' => env('RESEND_API_KEY'),
    ],

    'ses' => [
        'key' => env('AWS_ACCESS_KEY_ID'),
        'secret' => env('AWS_SECRET_ACCESS_KEY'),
        'region' => env('AWS_DEFAULT_REGION', 'us-east-1'),
    ],

    'slack' => [
        'notifications' => [
            'bot_user_oauth_token' => env('SLACK_BOT_USER_OAUTH_TOKEN'),
            'channel' => env('SLACK_BOT_USER_DEFAULT_CHANNEL'),
        ],
    ],

    /*
    |--------------------------------------------------------------------------
    | School Core API (Spring Boot microservice - Students)
    |--------------------------------------------------------------------------
    |
    | This library service does NOT own student data. Student records live in
    | the Spring Boot "school-core" service. We only ever talk to it over
    | HTTP and cache short-lived, read-only copies of what we need.
    |
    */
    'school_api' => [
        'url' => env('SCHOOL_API_URL', 'http://localhost:8080'),
        'token' => env('SCHOOL_API_TOKEN'),
        'timeout' => env('SCHOOL_API_TIMEOUT', 3),
        'cache_ttl' => env('SCHOOL_API_CACHE_TTL', 60), // seconds
    ],

];
