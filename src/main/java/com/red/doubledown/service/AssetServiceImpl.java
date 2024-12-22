package com.red.doubledown.service;

import com.red.doubledown.modal.Asset;
import com.red.doubledown.modal.Coin;
import com.red.doubledown.modal.User;
import com.red.doubledown.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService{

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset=new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        return asset;
    }

    @Override
    public Asset getAssetById(Long assetId) throws Exception {

        return assetRepository.findById(assetId)
                .orElseThrow(()->new Exception("asset not fond"));
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUsersAssets(Long userId) {
        return assetRepository.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {
        Asset oldasset=getAssetById(assetId);
        oldasset.setQuantity(quantity + oldasset.getQuantity());
        return assetRepository.save(oldasset);

    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepository.findByUserIdAndCoinId(userId,coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepository.deleteById(assetId);
    }
}
