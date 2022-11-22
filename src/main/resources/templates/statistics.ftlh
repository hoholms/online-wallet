<#import "parts/common.ftlh" as c>
<#import "parts/auth.ftlh" as l>

<@c.page>
    <div class="container-fluid">
        <h2>Statistics</h2>
        <div class="row">
            <div class="col-md-6 float-start">
                <canvas id="lineChart"></canvas>
            </div>
            <div class="col-md-3 float-end">
                <canvas id="incomeCircleChart" class="col-md-2"></canvas>
            </div>
            <div class="col-md-3 float-end">
                <canvas id="expenseCircleChart" class="col-md-2"></canvas>
            </div>
        </div>
    </div>
</@c.page>