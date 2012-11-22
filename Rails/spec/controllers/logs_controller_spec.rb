require 'spec_helper'

describe LogsController do

  describe "GET 'index'" do
    it "returns http success" do
      get :index
      response.should be_success
    end
  end

  describe "POST 'create'" do
    it "returns http success" do
      post :create, {:name => 'access_log', :line => 'xxxxyyyyy' }
      response.should be_success
    end
  end

end
