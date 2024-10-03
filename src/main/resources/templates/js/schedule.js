$(document).ready(function() {
    //alert('Working')
    $('#calendar').fullCalendar({
        header: {
            left: 'prev',
            center: 'title',
            right: 'next'
        },
        editable: true,
        events: [],
        eventRender: function(event, element) {
            element.css('background-color', event.color);
        }
    });

    $('#bookingForm').on('submit', function(e) {
        e.preventDefault();

        var date = $('#date').val();
        var time = $('#time').val();
        var datetime = date + 'T' + time;

        // Alert the scheduled time and date
        //alert('Scheduled time and date: ' + datetime);

        $('#calendar').fullCalendar('renderEvent', {
            title: 'Avaialable',
            start: datetime,
            color: '#21A86A'
        }, true);

        $('#bookingModal').modal('hide');
        $(this)[0].reset();
    });
});
