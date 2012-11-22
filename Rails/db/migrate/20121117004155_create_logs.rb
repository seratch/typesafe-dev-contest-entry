class CreateLogs < ActiveRecord::Migration
  def change
    create_table :logs do |t|
      t.string :name, :null => false
      t.text :line, :null => false
      t.timestamps
    end
  end
end
