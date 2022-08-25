(function () {
    angular
        .module('market-app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/market', {
                templateUrl: 'market/market.html',
                controller: 'marketController'
            })
            .when('/product_editor/:productId', {
                templateUrl: 'product_editor/product_editor.html',
                controller: 'productEditorController'
            })
            .when('/product_add', {
                templateUrl: 'product_add/product_add.html',
                controller: 'productAddController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.webMarketUser) {
            let jwtClaims = $localStorage.webMarketUser.token.split('.')[1];
            $rootScope.decodedJwtClaims = window.atob(jwtClaims);
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webMarketUser.token;
        }

        if ($localStorage.GuestUuid == null) {
            $http.get('http://localhost:8189/market/api/v1/guest')
                .then(function successCallback(response) {
                    $localStorage.GuestUuid = {
                        uuid: response.data.uuid
                    };
                }, function errorCallback(response) {
                    alert(response);
                });
        }
    }
})();

angular.module('market-app').controller('indexController', function ($rootScope, $scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    $rootScope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    console.log(response);
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webMarketUser = {
                        username: $scope.user.username,
                        token: response.data.token
                    };
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
                $http.post(contextPath + '/cart/merge', $localStorage.GuestUuid)
                    .then(function successCallback(response) {
                        delete $localStorage.GuestUuid;
                    }, function errorCallback(response) {
                        console.log(response);
                    });
                location.reload();
            }, function errorCallback(response) {
                alert(response.data.messages);
            });
    };

    $scope.tryToLogout = function () {
        if ($rootScope.isUserLoggedIn()) {
            $scope.clearUser();
            if ($scope.user) {
                $scope.user.username = null;
                $scope.user.password = null;
            }
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        return !!$localStorage.webMarketUser;
    };

    //надо переделать чтобы при обновлении страницы из под залогиненного юзера проверка не генерила кучу ошибок undefined в консоль
    $rootScope.isUserAdmin = function () {
        // $scope.roleAdmin = "ROLE_ADMIN";
        return $rootScope.isUserLoggedIn() && $rootScope.decodedJwtClaims.includes('ROLE_ADMIN');

    };
    //надо переделать чтобы при обновлении страницы из под залогиненного юзера проверка не генерила кучу ошибок undefined в консоль
    $rootScope.isUserManager = function () {
        // $scope.roleManager = "ROLE_MANAGER";
        return $rootScope.isUserLoggedIn() && $rootScope.decodedJwtClaims.includes('ROLE_MANAGER');

    };

    $scope.navToRegisterPage = function () {
        $location.path('/registration');
    }

});
