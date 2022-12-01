<#import "parts/common.ftlh" as c>

<@c.page>
    <h4 class="mb-4">List of transaction categories</h4>

    <form action="/categories" method="post" class="row g-3 mb-4">
        <input type="hidden" value="${_csrf.token}" name="_csrf">

        <div class="form-group col">
            <label class="form-label" for="category">Name</label>
            <input class="form-control" type="text" name="category" placeholder="Category name"
                   id="category" required>
        </div>

        <div class="form-group col">
            <label for="isIncome" class="col-sm-4 col-form-label">Is income</label>
            <div class="col-sm-8">
                <input type="checkbox" id="isIncome" name="isIncome"
                       class="form-check-input">
            </div>
        </div>

        <div class="form-group col">
            <label class="col-sm-4 form-label" for="subm"></label>
            <div class="col-sm-8">
                <input type="submit" class="btn btn-success" name="subbtn" value="Add category" id="subm">
            </div>
        </div>
    </form>

    <table class="table table-hover">
        <thead>
        <tr>
            <th>Category name</th>
            <th>Is income</th>
            <th>Save</th>
            <th>Delete</th>
        </tr>
        </thead>

        <tbody>
        <#list categories as category>
            <tr class="table-<#if category.isIncome>success<#else>danger</#if>">
                <td>${category.category}</td>
                <td><input type="checkbox" name="isIncome" id="isIncomeCheck"
                           class="form-check-input" ${category.isIncome?string("checked", "")} disabled>
                </td>
                <td>
                    <a class="btn btn-outline-primary btn-sm" href="/categories/${category.id}"
                       role="button">Edit</a>
                </td>
                <td>
                    <a class="btn btn-outline-danger btn-sm" href="/categories/delete/${category.id}"
                       role="button">Delete</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>