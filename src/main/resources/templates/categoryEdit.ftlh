<#import "parts/common.ftlh" as c>

<@c.page>
    <h4>Category editor</h4>
    <form action="/categories/edit" method="post">
        <input type="hidden" value="${_csrf.token}" name="_csrf">

        <div class="form-group row">
            <label for="categoryID" class="col-sm-2 col-form-label">Category ID</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext" value="${category.id}" name="id"
                       id="categoryID">
            </div>
        </div>

        <div class="form-group row">
            <label for="isIncome" class="col-sm-2 col-form-label">Is income</label>
            <div class="col-sm-10">
                <input type="checkbox" id="isIncome" name="isIncome"
                       class="form-check-input" ${category.isIncome?string("checked", "")}>
            </div>
        </div>

        <div class="form-group row mb-3">
            <label for="category" class="col-sm-2 col-form-label">Category name</label>
            <div class="col-sm-10">
                <input type="text" id="category" name="category" class="form-control" value="${category.category}"
                       placeholder="Category name" required>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-sm-10">
                <button class="btn btn-primary mt-3" type="submit">Save</button>
            </div>
        </div>
    </form>
</@c.page>