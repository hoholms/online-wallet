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

    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"
            integrity="sha512-ElRFoEQdI5Ht6kZvyzXhYG9NqjtkmlkfYk0wr6wHxU9JEHakS7UJZNeml5ALk+8IKlU6jDgMabC3vkumRokgJA=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="https://code.jquery.com/jquery-3.6.1.min.js"
            integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>

    <script type="text/javascript" src="/static/statistics.js"></script>
</@c.page>