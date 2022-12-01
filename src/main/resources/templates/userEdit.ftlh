<#import "parts/common.ftlh" as c>

<@c.page>
    <h4>User editor</h4>
    <form action="/users" method="post">
        <input type="hidden" value="${_csrf.token}" name="_csrf">

        <div class="form-group row">
            <label for="userID" class="col-sm-2 col-form-label">User ID</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext" value="${user.id}" name="userID" id="userID">
            </div>
        </div>

        <div class="form-group row">
            <label for="enabled" class="col-sm-2 col-form-label">Enabled</label>
            <div class="col-sm-10">
                <input type="checkbox" id="enabled" name="enabled"
                       class="form-check-input" ${user.isEnabled()?string("checked", "")}>
            </div>
        </div>

        <div class="form-group row mb-3">
            <label for="username" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-10">
                <input type="text" id="username" name="username" class="form-control" value="${user.username}"
                       placeholder="Username">
            </div>
        </div>

        <fieldset class="form-group">
            <div class="row">
                <legend class="col-form-label col-sm-2 pt-0">Roles</legend>
                <div class="col-sm-10">
                    <#list authorities as authority>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="${authority}"
                                   name="${authority}" ${user.authorities?seq_contains(authority)?string("checked", "")}>
                            <label for="${authority}" class="form-check-label">${authority}</label>
                        </div>
                    </#list>
                </div>
            </div>
        </fieldset>

        <div class="form-group row">
            <div class="col-sm-10">
                <button class="btn btn-primary mt-3" type="submit">Save</button>
            </div>
        </div>
    </form>
</@c.page>