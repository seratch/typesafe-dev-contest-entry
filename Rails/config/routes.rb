Railsapp::Application.routes.draw do

  root :to => 'root#index'
  resources :logs, :only => [:index, :create]
  post 'logs/async' => 'logs#async_create'

end
