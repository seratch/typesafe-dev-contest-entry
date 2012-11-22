require 'spec_helper'

describe RootController do

  describe "GET 'index'" do
    it "returns http success" do
      get 'index'
      response.status.should be(302)
      response.should redirect_to logs_path
    end
  end

end
