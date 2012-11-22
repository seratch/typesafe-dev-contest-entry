require 'spec_helper'

describe Log do

  it 'should have #recent' do
    latest_log = nil
    100.times.each do
      latest_log = FactoryGirl.create(:log)
    end
    recent_logs = Log.recent.limit(10)
    recent_logs[0].should eq(latest_log)
  end

end
