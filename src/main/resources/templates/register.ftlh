<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <div class="mb-1"><h5>Add new user</h5></div>
    <p class="text-danger">${error!""}</p>
    <@l.authRegister "/register" true/>
</@c.page>