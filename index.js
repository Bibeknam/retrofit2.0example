'use strict'

var express    = require('express'); 
var app        = express();                 
var bodyParser = require('body-parser');
var mongoose   = require('mongoose');

var Schema     = mongoose.Schema;

var UserSchema = new Schema({
    username: { type: String, required: true, index: { unique: true } },
    name: { type: String, required: true }
});

var User = mongoose.model('User', UserSchema);
mongoose.connect('mongodb://localhost/retrofit', function(err) {
	if(err) {
		console.log("Unable to connect to db :( : " + err)
	} else {
		console.log("Connected to database :)");
	}
});

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 8080; 
var router = express.Router();

router.get('/', function(req, res) {
    res.json({ message: 'Rest API for retrofit 2.0 application' });   
});

router.route('/user')
		.post(function(req, res) {
			var user = new User();
			user.username = req.body.username;
			user.name = req.body.name;

			user.save(function(user, err) {
				if (err)
                	res.send(err);
                else
            		res.json({ message: 'User Created!' });
			});
		})
		.get(function(req, res) {
			var formattedUsers = [];
			User.find(function(err, users) {
            	if (err)
                	res.send(err);
                else {
                	users.forEach(function(user) {
                		var formattedUser = {};
                		formattedUser.username = user.username;
                		formattedUser.name = user.name;
                		formattedUsers.push(formattedUser);
                	});

                	res.json(formattedUsers);
            	}	
        	});
		});

app.use('/api', router);

app.listen(port);