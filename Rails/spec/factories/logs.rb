# Read about factories at https://github.com/thoughtbot/factory_girl

FactoryGirl.define do

  factory :log do
    name "access_log"
    line <<LINE
10.146.45.58 - - [11/Nov/2012:03:21:02 +0000] "GET / HTTP/1.1" 200 7505 "-" "ELB-HealthChecker/1.0"
LINE
  end

end
