var module = require('main_module');

function Controller(UserService) {

  var vm = this;
  vm.order = {};
  vm.user = {};
  vm.createOrder = createOrder;


  vm.$onInit = function() {
          _loadOrderDetails();
  };

    function _loadOrderDetails() {
        UserService.get().then(
            function (response) {
                vm.user = response.data;
                _setOrder();
            },
            function (err) {
                console.log('Error', err);
            });
    }

    function _setOrder() {
        vm.order.email = vm.user.email;
        vm.order.phone = vm.user.phone;
    }

    function createOrder(){
        UserService.createOrder().then(
            function(response) {
             vm.message = true;
            },
            function (err) {
             vm.error = true;
            }
        );
    }

}
Controller.$inject = ['UserService'];
require('./order_component.scss');
module.component('orderComponent', {
    controller: Controller,
    templateUrl: require('./order_component.html')
});