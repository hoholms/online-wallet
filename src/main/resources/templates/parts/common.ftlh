<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="theme-color" content="#ffffff">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi"
              crossorigin="anonymous">

        <title>Online Wallet</title>

        <link rel="icon" href="/static/favicon/favicon.svg">
        <link rel="apple-touch-icon" href="/static/favicon/favicon-apple.png">
        <link rel="manifest" href="/static/favicon/manifest.json">
        <link rel="shortcut icon" href="/static/favicon/favicon-32.png">

        <link rel="stylesheet" href="/static/style.css">
        <link rel="stylesheet" href="/static/sidebar.css">
    </head>
    <body>
    <div class="container-fluid">
        <div class="row">
            <#include "sidebar.ftlh">
            <div class="col-md-main col-lg-main ms-sm-6 px-md-4 mt-4" style="z-index: 1">
                <#nested>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="/static/sidebar.js"></script>
    </body>
    </html>
</#macro>