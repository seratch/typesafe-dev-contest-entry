require 'eventmachine'

class LogsController < ApplicationController

  protect_from_forgery :except => [:create, :async_create]

  def index
    @logs = Log.recent.limit(100)
  end

  def create
    log = Log.new
    log.name = params[:name]
    log.line = params[:line]
    log.save!
    render :status => :ok, :text => nil
  end

  def async_create
    EM.run do
      log = Log.new
      log.name = params[:name]
      log.line = params[:line]
      log.save!
    end
    render :status => :accepted, :text => nil
  end

end
