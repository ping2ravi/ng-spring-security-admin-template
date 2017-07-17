var adminApp = angular.module('admin', ['ngRoute', 'blockUI']);
//var adminApp = angular.module('admin', ['ngRoute', 'ngResource', 'blockUI']);

adminApp.config(function($routeProvider, $httpProvider, $locationProvider) {

	$routeProvider.when('/', {
		templateUrl : 'admin/dashboard.html',
        controller : 'dashboard'
	}).when('/home', {
      		templateUrl : 'home.html',
      		controller : 'home',
    }).when('/login', {
		templateUrl : 'admin/auth/login.html',
		controller : 'navigation'
	}).when('/admin/dashboard', {
      		templateUrl : 'admin/dashboard.html',
      		controller : 'dashboard'
    }).when('/admin/user/profile', {
            templateUrl : 'admin/user/profile.html',
            controller : 'profile'
    }).when('/admin/ext/charts', {
            templateUrl : 'admin/ext/charts.html',
            controller : 'profile'
    }).when('/admin/ext/forms', {
            templateUrl : '/admin/ext/forms.html',
            controller : 'generic'
    }).when('/admin/ext/bootstrap-elements', {
            templateUrl : '/admin/ext/bootstrap-elements.html',
            controller : 'generic'
    }).when('/admin/ext/bootstrap-grid', {
            templateUrl : '/admin/ext/bootstrap-grid.html',
            controller : 'generic'
    }).when('/admin/ext/index-rtl', {
            templateUrl : '/admin/ext/index-rtl.html',
            controller : 'generic'
    }).when('/admin/ext/blank-page', {
            templateUrl : '/admin/ext/blank-page.html',
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


