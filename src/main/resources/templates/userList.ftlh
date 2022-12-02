<#import "parts/common.ftlh" as c>

<@c.page>
    <h4>List of users</h4>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Role</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td><#list user.authority as authority>${authority}<#sep>, </#list></td>
                <td><a class="btn btn-outline-primary btn-sm" href="/users/${user.id}"
                       role="button">Edit</a></td>
                <td><a class="btn btn-outline-danger btn-sm" href="/users/delete/${user.id}"
                       role="button">Delete</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>