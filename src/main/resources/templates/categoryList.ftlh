<#import "parts/common.ftlh" as c>

<@c.page>
    <h4 class="pb-4">List of transaction categories</h4>

    <form action="/categories" method="post" class="pb-4 row g-4 align-items-center border rounded">
        <input type="hidden" value="${_csrf.token}" name="_csrf">

        <div class="form-group col-auto">
            <label class="form-label" for="category">Name</label>
            <input class="form-control" type="text" name="category" placeholder="Category name"
                   id="category" required>
        </div>

        <div class="form-group col-auto">
            <label for="isIncome" class="col-sm-4 form-label">Income</label>
            <div class="col-sm-8">
                <input type="checkbox" id="isIncome" name="isIncome"
                       class="form-check-input">
            </div>
        </div>

        <div class="form-group col-auto">
            <label class="col-sm-4 form-label" for="subm"></label>
            <div class="col-sm-8">
                <input type="submit" class="btn btn-success" name="subbtn" value="Add category" id="subm">
            </div>
        </div>
    </form>

    <div class="card mt-3">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Category name</th>
                <th style="text-align: center">Is income</th>
                <th style="text-align: center">Edit</th>
                <th style="text-align: center">Delete</th>
            </tr>
            </thead>

            <tbody>
            <#list categories as category>
                <tr>
                    <td>${category.category}</td>
                    <td class="table-<#if category.isIncome>success<#else>danger</#if>"
                        style="width: 10%; text-align: center">
                        <input type="checkbox" name="isIncome" id="isIncomeCheck"
                               class="form-check-input" ${category.isIncome?string("checked", "")} disabled>
                    </td>
                    <td style="width: 10%; text-align: center">
                        <a class="btn btn-outline-primary btn-sm" href="/categories/${category.id}"
                           role="button">Edit</a>
                    </td>
                    <td style="width: 10%; text-align: center">
                        <a class="btn btn-outline-danger btn-sm" href="/categories/delete/${category.id}"
                           role="button">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>