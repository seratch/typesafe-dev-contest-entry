class Log < ActiveRecord::Base

  attr_accessible :name, :line

  scope :recent, lambda { order(:created_at).reverse_order }

end
