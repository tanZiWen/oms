<!DOCTYPE html>
<html>
<head>
    <title>Twitter Bootstrap Typeahead Extension Demo</title>
    <link href="css/bootstrap.css" rel="stylesheet">
    <script src="js/jquery.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
    
    
    <link href="css/prettify.css" rel="stylesheet" />
    <link href="css/demo.css" rel="stylesheet">
    <link href="css/bootstrapValidator.min.css" rel="stylesheet">
    <link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="js/prettify.js" type="text/javascript"></script>
    <script src="js/mockjax.js" type="text/javascript"></script>
    <script src="js/bootstrap-typeahead.js" type="text/javascript"></script>
    <script src="js/demo.js" type="text/javascript"></script>
    <script src="js/bootstrapValidator.min.js" type="text/javascript"></script>
    <script src="js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
    
    <script>
    	$(function (){
    		$(".dropdown-menu").css("z-index","1051");
    		
    		$('#togglingForm')
            .bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                submitHandler: function(validator, form, submitButton) {
                    // Do nothing
                },
                fields: {
                    firstName: {
                        validators: {
                            notEmpty: {
                                message: 'The first name is required'
                            }
                        }
                    },
                    lastName: {
                        validators: {
                            notEmpty: {
                                message: 'The last name is required'
                            }
                        }
                    },
                    company: {
                        validators: {
                            notEmpty: {
                                message: 'The company name is required'
                            }
                        }
                    },
                    // These fields will be validated when being visible
                    job: {
                        validators: {
                            notEmpty: {
                                message: 'The job title is required'
                            }
                        }
                    },
                    department: {
                        validators: {
                            notEmpty: {
                                message: 'The department name is required'
                            }
                        }
                    },
                    mobilePhone: {
                        validators: {
                            notEmpty: {
                                message: 'The mobile phone number is required'
                            },
                            digits: {
                                message: 'The mobile phone number is not valid'
                            }
                        }
                    },
                    // These fields will be validated when being visible
                    homePhone: {
                        validators: {
                            digits: {
                                message: 'The home phone number is not valid'
                            }
                        }
                    },
                    officePhone: {
                        validators: {
                            digits: {
                                message: 'The office phone number is not valid'
                            }
                        }
                    }
                }
            })
            .find('button[data-toggle]')
                .on('click', function() {
                    var $target = $($(this).attr('data-toggle'));
                    // Show or hide the additional fields
                    // They will or will not be validated based on their visibilities
                    $target.toggle();
                    if (!$target.is(':visible')) {
                        // Enable the submit buttons in case additional fields are not valid
                        $('#togglingForm').data('bootstrapValidator').disableSubmitButtons(false);
                    }
                });
    		
    		
    	});
    </script>

</head>
<body class="container">

<form id="togglingForm" method="post" class="form-horizontal">
    <div class="form-group">
        <label class="col-lg-3 control-label">Full name <sup>*</sup></label>
        <div class="col-lg-4">
            <input type="text" class="form-control" name="firstName" placeholder="First name" />
        </div>
        <div class="col-lg-4">
            <input type="text" class="form-control" name="lastName" placeholder="Last name" />
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 control-label">Company <sup>*</sup></label>
        <div class="col-lg-5">
            <input type="text" class="form-control" name="company"
                   required data-bv-notempty-message="The company name is required" />
        </div>
        <div class="col-lg-2">
            <button type="button" class="btn btn-info btn-sm" data-toggle="#jobInfo">
                Add more info
            </button>
        </div>
    </div>

    <!-- These fields will not be validated as long as they are not visible -->
    <div id="jobInfo" style="display: none;">
        <div class="form-group">
            <label class="col-lg-3 control-label">Job title <sup>*</sup></label>
            <div class="col-lg-5">
                <input type="text" class="form-control" name="job" />
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">Department <sup>*</sup></label>
            <div class="col-lg-5">
                <input type="text" class="form-control" name="department" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-lg-3 control-label">Mobile phone <sup>*</sup></label>
        <div class="col-lg-5">
            <input type="text" class="form-control" name="mobilePhone" />
        </div>
        <div class="col-lg-2">
            <button type="button" class="btn btn-info btn-sm" data-toggle="#phoneInfo">
                Add more phone numbers
            </button>
        </div>
    </div>

    <!-- These fields will not be validated as long as they are not visible -->
    <div id="phoneInfo" style="display: none;">
        <div class="form-group">
            <label class="col-lg-3 control-label">Home phone</label>
            <div class="col-lg-5">
                <input type="text" class="form-control" name="homePhone" />
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-3 control-label">Office phone</label>
            <div class="col-lg-5">
                <input type="text" class="form-control" name="officePhone" />
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-lg-9 col-lg-offset-3">
            <button type="submit" class="btn btn-primary">Validate</button>
        </div>
    </div>
</form>


    <div class="row">
        <div class="well span4 form-group">
            <input id="demo1" name="demo1" type="text" class="span4" data-bv-phone data-bv-phone-message="The gender is required" placeholder="Search cities..." autocomplete="off" />
        </div>
		<pre class="prettyprint span7">
		    $('#demo1').typeahead({
		        source: [
		            { id: 1, name: 'Toronto' },
		            { id: 2, name: 'Montreal' },
		            { id: 3, name: 'New York' },
		            { id: 4, name: 'Buffalo' },
		            { id: 5, name: 'Boston' },
		            { id: 6, name: 'Columbus' },
		            { id: 7, name: 'Dallas' },
		            { id: 8, name: 'Vancouver' },
		            { id: 9, name: 'Seattle' },
		            { id: 10, name: 'Los Angeles' }
		        ]
		    });
		</pre>
    </div>
   

</body>
</html>
