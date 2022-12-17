<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <@l.auth "/register" true/>
    <script>
        window.onload = function () {
            document.getElementById("uname").focus();
        }
    </script>
</@c.page>