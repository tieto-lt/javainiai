var module = require('main_module');

function Controller($scope,OrdersService) {

    var vm = this;
    vm.orders = {};
    vm.information = {};
    vm.result = [];
    vm.message = false;
    $scope.oneAtATime = true;
    vm.getResults= getResults;
    vm.fakeOrder = [];
    vm.selected = [];
    vm.list = [];
    vm.videoUrl = undefined;

   // vm.getObjects = getObjects;
    vm.all = [
        {id :0, selected: false, color: "#928f8f", name: "1 object", size: 55, text:"#171313", width: 5, camera: "front"},
        {id :1, selected: false, color: "#928f8f", name: "2 object", size: 55, text:"#171313", width: 5, camera: "front"},
        {id :2, selected: false, color: "#928f8f", name: "3 object", size: 55, text:"#171313", width: 5, camera: "front"},
        {id :3, selected: false, color: "#928f8f", name: "4 object", size: 55, text:"#171313", width: 5, camera: "front"}
      ];

    vm.myInterval = 3000;
    vm.noWrapSlides = false;
    vm.activeSlide = 0;

    vm.$onInit = function() {
            _loadList();

        };

    function _loadList() {
        OrdersService.getAllOrders().then(
            function (response) {
                vm.orders = response.data;
                if(vm.orders.length == 0){
                vm.message = true;
                }

                $scope.status = false;
            },
            function (err) {
                console.log('Error',err);
            });
     }
     $scope.status = {
       isCustomHeaderOpen: false,
       isFirstOpen: true,
       isFirstDisabled: false
     };

    function getResults (orderId) {
        OrdersService.getOrderResults(orderId).then(
            function(response){
               vm.result=response.data[0];
               if(vm.result == null){
                   console.log("null");
               }
               vm.videoUrl = "/api/missionsUI/video/" + vm.result.id;
            }
        );
        for(i=0; i<vm.orders.length;i++){
            if(vm.orders[i].id == orderId){
                //console.log(vm.orders[i].mapItems);

                for(j=0; j<vm.orders[i].mapItems.length; j++){
                   //console.log(vm.orders[i].mapItems[j].name);
                   for(a = 0; a<4; a++){
                        if(vm.orders[i].mapItems[j].name == vm.all[a].name){
                            vm.all[a].color = "#009688";
                        }
                   }
                }
            }
        }
     }


        $scope.test = function(text) {
          alert(text);
        }



}

Controller.$inject = ['$scope','OrdersService'];
require('./list.scss');
module.component('customerOrders', {
    controller: Controller,
    templateUrl: require('./list.html')
});