<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <div class="mb-1">
        <h2>Statistics</h2>
        <form class="row g-3" action="/statistics" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <div class="col-md-2 form-group">
                <label class="form-label" for="tDate1">Date1</label>
                <input class="form-control" type="date" value="${today}" name="transactionDate1" id="tDate1"
                       style="height: 2.4rem">
            </div>
            <div class="col-md-2 form-group">
                <label class="form-label" for="tDate2">Date2</label>
                <input class="form-control" type="date" value="${today}" name="transactionDate2" id="tDate2"
                       style="height: 2.4rem">
            </div>
            <div class="col-12">
                <button type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>

        <div class="container">
            <canvas id="myChart"></canvas>
        </div>
    </div>
</@c.page>