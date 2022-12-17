<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi"
              crossorigin="anonymous">

        <title>Online Wallet</title>

        <link rel="icon" href="/static/favicon/favicon.svg">
        <link rel="apple-touch-icon" href="/static/favicon/favicon-apple.png">
        <link rel="manifest" href="/static/favicon/manifest.json">
        <link rel="shortcut icon" href="/static/favicon/favicon-32.png" sizes="any">

        <meta name="theme-color" content="#ffffff">

        <link rel="stylesheet" href="/static/style.css">
        <link rel="stylesheet" href="/static/sidebar.css">
        <link rel="stylesheet" href="/static/heroes.css">
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.6.1.min.js"
            integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tilt.js/1.2.1/tilt.jquery.min.js"></script>

    <script type="text/javascript" src="/static/sidebar.js"></script>
    </body>
    </html>
</#macro>