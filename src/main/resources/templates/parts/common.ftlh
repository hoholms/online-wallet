<#macro page>
    <!DOCTYPE html>
    <html lang="en" data-bs-theme="light">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
                crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"
                integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
        <title>Online Wallet</title>

        <link rel="icon" href="/static/favicon/favicon.svg">
        <link rel="apple-touch-icon" href="/static/favicon/favicon-apple.png">
        <link rel="manifest" href="/static/favicon/manifest.json">
        <link rel="shortcut icon" href="/static/favicon/favicon-32.png" sizes="any">

        <meta name="theme-color" content="#ffffff">

        <link rel="stylesheet" href="/static/style/style.css">
        <link rel="stylesheet" href="/static/style/sidebar.css">
        <link rel="stylesheet" href="/static/style/heroes.css">
    </head>
    <body>
    <div class="container-fluid">
        <div class="row min-vh-100 d-flex flex-column">
            <div class="col-md-main col-lg-main ms-sm-6 px-md-4 mt-4">
                <#nested>
            </div>
            <div class="nav-wrapper" style="height:3.78rem;">
                <#include "sidebar.ftlh">
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/tilt.js/1.2.1/tilt.jquery.min.js"></script>
    <script type="text/javascript" src="/static/script/sidebar.js"></script>
    <script type="text/javascript" src="/static/script/colorTheme.js"></script>
    </body>
    </html>
</#macro>