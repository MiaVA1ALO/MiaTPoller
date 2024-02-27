Sends POST requests to websites utilizing the WordPress plugin TotalPoll, as if a user was participating it.
Can alter the polls dramatically as this program sends as many requests as the site will let you.
Note: TotalPoll has the option to block an IP address for an amount of time. If enabled, this won't work more than once.
The reason why this script works is because by default, TotalPoll uses cookies to check who has already filled out the form.

Usage: MiaTPoller domain pollID choiceIndex numTimes

domain is any website that uses the WordPress plugin TotalPoll.
Examples: www.pollwordpresssite.com, pollwordpresssite.com, pollwordpresssite.com/wp-admin/admin-ajax.php, etc.
Any variation of the example above works as long as the input references either the root of the website or the admin-ajax.php file used to control TotalPoll.

pollID is the totalpoll[id] value. It can be found using Inspect Element on the poll form.
Examples: 1234, 10021, 999, etc

choiceIndex is the selection you would like to use in the poll. It starts at 0 and goes up by 1 each option.
Example: What is your favourite animal? A: Dog, B: Cat, C: Goldfish - A would be 0, B is 1, and C 2.

numTimes is the number of times to repeat the process. -1 will go forever until stack overflow or until the website
thinks you're trying to DDOS it.
Examples: 100, 13, -1, 8, etc
