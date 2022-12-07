<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <#if !user??>
        <div class="px-4 my-5 text-center border-bottom">
            <h1 class="display-4 fw-bold">Online Wallet</h1>
            <div class="col-lg-6 mx-auto">
                <p class="lead mb-4">Keep track of your expenses and income so you no longer wonder where all the money
                    went. Detailed personal statistics will help you keep track of your progress.</p>
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-5">
                    <a href="/register" class="btn btn-primary btn-lg px-4 me-sm-3">Sign Up</a>
                    <a href="/login" class="btn btn-outline-secondary btn-lg px-4">Login</a>
                </div>
            </div>
            <div class="overflow-hidden" style="max-height: 30vh;">
                <div class="container px-5">
                    <img src="/static/img/dashboard.png" class="img-fluid border rounded-3 shadow-lg mb-4"
                         alt="Dashboard" width="700" height="500" loading="lazy">
                </div>
            </div>
        </div>
    <#else>
        <div class="row text-center">
            <h1 class="mb-1 text-center">Hello, ${name}!</h1>
            <h2 class="mb-3 text-center">Here you can control your expenses.</h2>
            <div class="mx-auto">
                <a href="/dashboard" class="btn btn-primary btn-lg">Go to dashboard</a>
            </div>
        </div>
    </#if>
</@c.page>