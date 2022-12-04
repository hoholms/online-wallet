/*** Charts html elements ***/
const lineStatistics = document.getElementById('lineChart').getContext("2d");
const incomeCircleStatistics = document.getElementById('incomeCircleChart');
const expenseCircleStatistics = document.getElementById('expenseCircleChart');

/*** Dates selection ***/
var fromData = document.getElementById("from");
var toData = document.getElementById("to");

/*** Gradient ***/
var greenGradient = lineStatistics.createLinearGradient(0, 25, 0, document.getElementById('lineChart').offsetHeight * 4);
greenGradient.addColorStop(0, 'rgba(25, 135, 84,1)');
greenGradient.addColorStop(1, 'rgba(25, 135, 84,0)');

var redGradient = lineStatistics.createLinearGradient(0, 25, 0, document.getElementById('lineChart').offsetHeight * 4);
redGradient.addColorStop(0, 'rgba(165, 29, 42,1)');
redGradient.addColorStop(1, 'rgba(165, 29, 42,0)');

/*** Font ***/
Chart.defaults.font.family = document.getElementById('statTitle').style.fontFamily;


/*** Charts ***/
var lineChart, incomeCircleChart, expenseCircleChart;
$.getJSON('/statistics/line', function (data) {drawLineStatistics(data)});
$.getJSON('/statistics/circle', function (data) {drawCircleStatistics(data)});
$('.statForm').change(function () {
    var token = $("meta[name='_csrf']").attr("content");

    lineChart.destroy();
    incomeCircleChart.destroy();
    expenseCircleChart.destroy();

    $.ajax({
        url: '/statistics/line',
        type: 'post',
        data: {
            _csrf:token,
            from:fromData.options[fromData.selectedIndex].text,
            to:toData.options[toData.selectedIndex].text
        },
        dataType: 'json',
        success: function (data) {drawLineStatistics(data)}
    });

    $.ajax({
        url: '/statistics/circle',
        type: 'post',
        data: {
            _csrf:token,
            from:fromData.options[fromData.selectedIndex].text,
            to:toData.options[toData.selectedIndex].text
        },
        dataType: 'json',
        success: function (data) {drawCircleStatistics(data)}
    });
});

/*** Draw chart functions ***/
function drawLineStatistics(data) {
    lineChart = new Chart(lineStatistics, {
        type: 'line',
        data: {
            labels: data[0].labels,
            datasets: [
                {
                    backgroundColor: greenGradient,
                    fill: true,
                    label: 'Earned',
                    data: data[0].values,
                    borderColor: 'rgb(25, 135, 84)',
                    pointBorderColor: 'rgb(25, 135, 84)',
                    pointBackgroundColor: 'rgb(25, 135, 84)',
                    lineTension: 0.3,
                    borderWidth: 2,
                    pointRadius: 3
                },
                {
                    backgroundColor: redGradient,
                    fill: true,
                    label: 'Spent',
                    data: data[1].values,
                    borderColor: 'rgb(165, 29, 42)',
                    pointBorderColor: 'rgb(165, 29, 42)',
                    pointBackgroundColor: 'rgb(165, 29, 42)',
                    lineTension: 0.3,
                    borderWidth: 2,
                    pointRadius: 3
                }
            ]
        },
        options: {
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            let label = context.dataset.label || '';

                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed.y !== null) {
                                label += new Intl.NumberFormat('en-US', {
                                    style: 'currency',
                                    currency: 'MDL'
                                }).format(context.parsed.y);
                            }
                            return label;
                        }
                    }
                }
            },
            layout: {
                padding: 10
            },
            responsive: true,
            scales: {
                x: {
                    grid: {
                        display: false
                    }
                },
                y: {
                    grid: {
                        display: true,
                    }
                }

            }
        },
    });
}
function drawCircleStatistics (data) {
    incomeCircleChart = new Chart(incomeCircleStatistics, {
        type: 'doughnut',
        data: {
            labels: data[0].categories,
            datasets: [
                {
                    label: 'Earned',
                    data: data[0].values,
                    backgroundColor: [
                        'rgb(25, 135, 84)',
                        'rgb(46,139,87)',
                        'rgb(143,188,143)',
                        'rgb(0,250,154)',
                        'rgb(50,205,50)',
                        'rgb(20, 108, 67)',
                        'rgb(128,128,0)'
                    ],
                    borderColor: 'rgb(20, 20, 20)'
                },
            ]
        },
        options: {
            plugins: {
                legend: {
                    position: 'right'
                }
            }
        }
    });

    expenseCircleChart = new Chart(expenseCircleStatistics, {
        type: 'doughnut',
        data: {
            labels: data[1].categories,
            datasets: [
                {
                    label: 'Spent',
                    data: data[1].values,
                    backgroundColor: [
                        'rgb(165, 29, 42)',
                        'rgb(128, 0, 32)',
                        'rgb(170, 74, 68)',
                        'rgb(210, 4, 45)',
                        'rgb(227, 66, 52)',
                        'rgb(129, 65, 65)',
                        'rgb(119, 7, 55)'
                    ],
                    borderColor: 'rgb(20, 20, 20)'
                },
            ]
        },
        options: {
            plugins: {
                legend: {
                    position: 'right'
                }
            }
        }
    });
}