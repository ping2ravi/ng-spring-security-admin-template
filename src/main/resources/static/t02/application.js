var adminApp = angular.module('admin', ['ngRoute', 'blockUI']);
//var adminApp = angular.module('admin', ['ngRoute', 'ngResource', 'blockUI']);

adminApp.config(function($routeProvider, $httpProvider, $locationProvider) {

	$routeProvider.when('/', {
		templateUrl : 'admin/dashboard.html',
        controller : 'dashboard'
    }).when('/login', {
		templateUrl : 'admin/auth/login.html',
		controller : 'navigation'
	}).when('/admin/charts/flot', {
      		templateUrl : 'admin/charts/flot.html',
      		controller : 'generic'
    }).when('/admin/tables', {
            templateUrl : 'admin/tables.html',
            controller : 'generic'
    }).when('/admin/forms', {
            templateUrl : 'admin/forms.html',
            controller : 'generic'
    }).when('/admin/ui-elements/panels', {
            templateUrl : '/admin/ui-elements/panels.html',
            controller : 'generic'
    }).when('/admin/ui-elements/buttons', {
            templateUrl : '/admin/ui-elements/buttons.html',
            controller : 'generic'
    }).when('/admin/ui-elements/notifications', {
            templateUrl : '/admin/ui-elements/notifications.html',
            controller : 'generic'
    }).when('/admin/ui-elements/typography', {
            templateUrl : '/admin/ui-elements/typography.html',
            controller : 'generic'
    }).when('/admin/ui-elements/icons', {
            templateUrl : '/admin/ui-elements/icons.html',
            controller : 'generic'
    }).when('/admin/ui-elements/grid', {
            templateUrl : '/admin/ui-elements/grid.html',
            controller : 'generic'
    }).when('/admin/sample/blank', {
            templateUrl : '/admin/sample/blank.html',
            controller : 'generic'
    }).when('/admin/sample/login', {
            templateUrl : '/admin/sample/login.html',
            controller : 'generic'
    }).otherwise('/admin/dashboard');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

    if(window.history && window.history.pushState){
        $locationProvider.html5Mode(true);
      }


});
adminApp.run(['$rootScope', '$location', '$http', function($rootScope, $location, $http){
          $rootScope.$on('$routeChangeStart', function (event) {
          $rootScope.previousUrl = $location.path();
          if (!$rootScope.authenticated) {
              console.log('DENY');
              //event.preventDefault();
              $location.path('/login');
          }
          else {
              console.log('ALLOW');
          }
          });


          $rootScope.logout = function() {
            $http.post('logout', {}).finally(function() {
                $rootScope.authenticated = false;
                $location.path("/login");
            });
        }
  }]);

adminApp.directive('date', function (dateFilter) {
    return {
        require:'ngModel',
        link:function (scope, elm, attrs, ctrl) {

            var dateFormat = attrs['date'] || 'yyyy-MM-dd';

            ctrl.$formatters.unshift(function (modelValue) {
                return dateFilter(modelValue, dateFormat);
            });
        }
    };
})

adminApp.controller('navigation', ['$scope', '$rootScope', '$http', '$location', '$route', function($scope, $rootScope, $http, $location, $route) {
			
			var authenticate = function(credentials, callback) {

				var headers = credentials ? {
					authorization : "Basic "
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};

				$http.get('user', {
					headers : headers
				}).then(function(response) {
					if (response.data.name) {
						$rootScope.authenticated = true;
					} else {
						$rootScope.authenticated = false;
					}
					callback && callback($rootScope.authenticated);
				}, function() {
					$rootScope.authenticated = false;
					callback && callback(false);
				});

			}

			authenticate();

			$scope.credentials = {};
			$scope.login = function() {
				authenticate($scope.credentials, function(authenticated) {
					if (authenticated) {
						console.log("Login succeeded")
						if($rootScope.previousUrl){
						    $location.path($rootScope.previousUrl);
						}else{
						    $location.path("/");
						}
						$scope.error = false;
						$rootScope.authenticated = true;
					} else {
						console.log("Login failed")
						$location.path("/login");
						$scope.error = true;
						$rootScope.authenticated = false;
					}
				})
			};



}]);

adminApp.controller('home', ['$http', function($http) {
	$http.get('/resource/').then(function(response) {
		$scope.greeting = response.data;
	})
}]);

adminApp.controller('profile', function($http) {
});

adminApp.controller('dashboard', function($http) {

});

adminApp.controller('generic', function($http) {

});


